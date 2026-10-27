import React, {useState} from 'react';
import { isValidTitle } from '../validation.mjs';

function IdeaForm({ onSubmit, isSaving }){
    const [title, setTitle] = useState('');
    const [category, setCategory] = useState('Clubs'); //Default
    const [validationError, setValidationError] = useState('');
    
    const handleFormSubmit = (e) => { 
        //Prevent Browser page reload
        e.preventDefault();
        setValidationError('');

        //Trim and validate title using helper function
        if(!isValidTitle(title)){
            setValidationError('Title cannot be empty or contains only spaces. ');
            return;
        }

        //passing object to handler inside App.jsx
        onSubmit({ title: title.trim(), category});

        //Clear title text field after successful exection
        setTitle('');
    };

    return(
        <form onSubmit={handleFormSubmit}>
            <h3>Submit an Idea</h3>

            <div className="form-group">
                <label htmlFor ="idea-title">Idea Title:</label>
                <br />
                <input 
                id="idea-title"
                type="text"
                value={title}
                onChange={(e)=> setTitle(e.target.value)}
                disabled={isSaving}
                placeholder="e.g, Start a coding club"
                />
                {validationError &&(
                    <p className="validation-error" style={{ color: 'red', fontSize: '0.85rem'}}>⚠️ {validationError}</p>
                )}
            </div>

            <div className="form-group" style={{ marginTop: '10px', marginBottom: '15px'}}>
                <label htmlFor="idea-category">Category:</label>
                <br />
                <input
                    id="idea-cetegory"
                    type="text"
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                    disabled={isSaving}
                    placeholder="Enter a category"
                />
            </div>

            {/* Step 4 constraint: Prevent repeat submissions by locking the button */}
            <button type="submit" disabled={isSaving}>
                {isSaving ? 'Saving idea...' : 'Submit Entry'}
            </button>

        </form>
    );
}

export default IdeaForm;