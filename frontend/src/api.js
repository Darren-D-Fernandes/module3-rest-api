const API_BASE_URL = 'http://localhost:8080/api/ideas';

export async function fetchIdeas(){
    const response = await fetch(API_BASE_URL);
    if(!response.ok){
        throw new Error(`Failed to load ideas (Status: ${response.status})`);

    }
    return response.json();
}

export async function createIdea(ideaData){
    const response = await fetch(API_BASE_URL, {
        method: 'POST',
        headers:{
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(ideaData),

    });
    if(!response.ok){
        throw new Error(`Failed to dave idea (Status: ${response.status})`);

    }
    return response.json();
}
