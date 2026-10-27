import React from 'react';

function IdeaList({ ideas }){
    //if no ideas exist
    if(ideas.length === 0){
        return(
            <div>
                No ideas yet. Add the first one.
            </div>
        );
    }

    return(
        <div>
            <h3>Campus Board Submissions</h3>
            <ul>
                {ideas.map((idea)=>(
                    <li key={idea.id} >
                        <strong>{idea.title}</strong> <br/>
                        Category: {idea.category}
                    </li>
                ))}
            </ul>
        </div>
    );
}


export default IdeaList;
