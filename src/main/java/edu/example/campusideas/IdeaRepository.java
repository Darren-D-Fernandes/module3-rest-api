package edu.example.campusideas;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Plain JDBC study component. Register as a Spring bean in your application.
public class IdeaRepository {
    private final String url;

    public IdeaRepository(Path path) throws IOException, SQLException {
        Path absolute = path.toAbsolutePath();
        Files.createDirectories(absolute.getParent());
        url = "jdbc:sqlite:" + absolute;
        try (Connection conn = connect();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS ideas (
                    id INTEGER PRIMARY KEY,
                    title TEXT NOT NULL CHECK(length(trim(title)) > 0),
                    category TEXT NOT NULL CHECK(length(trim(category)) > 0)
                )
                """);
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public List<Idea> findAll() throws SQLException {
        String sql = "SELECT id, title, category FROM ideas ORDER BY id";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rows = stmt.executeQuery()) {
            List<Idea> ideas = new ArrayList<>();
            while (rows.next()) {
                ideas.add(new Idea(rows.getLong("id"),
                    rows.getString("title"), rows.getString("category")));
            }
            return ideas;
        }
    }

    public Idea create(String title, String category) throws SQLException {
        if (title == null || category == null
                || title.isBlank() || category.isBlank()) {
            throw new IllegalArgumentException("Title and category must not be blank");
        }
        String cleanTitle = title.strip();
        String cleanCategory = category.strip();
        String sql = "INSERT INTO ideas(title, category) VALUES (?, ?) "
                   + "RETURNING id, title, category";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cleanTitle);
            stmt.setString(2, cleanCategory);
            try (ResultSet row = stmt.executeQuery()) {
                if (!row.next()) throw new SQLException("No created row returned");
                return new Idea(row.getLong("id"), row.getString("title"),
                                row.getString("category"));
            }
        }
    }
}

