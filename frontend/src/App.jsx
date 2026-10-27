import React, { useEffect, useState } from 'react'
import IdeaList from './components/IdeaList';
import IdeaForm from './components/IdeaForm';
import { fetchIdeas, createIdea } from './api';


function App() {
  const [ideas, setIdeas] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [loadError, setLoadError] = useState(null);
  const [isSaving, setIsSaving] = useState(false);
  const [saveError, setSaveError] = useState(null);
  const [statusMessage, setStatusMessage] = useState('');

  //Core async function to fetch data from the server
  const loadInitialIdeas = async () =>{
    setIsLoading(true);
    setLoadError(null);
    try{
      const data = await fetchIdeas();
      setIdeas(data);
    } catch(err){
      setLoadError(err.message || 'Could not connect to the backend server.');
    } finally{
      setIsLoading(false);
    }
  };

  //Run the Effect on first GET and clean up obsolet reads
  useEffect(()=>{
    let isMounted = true;

    if(isMounted){
      loadInitialIdeas();
    }

    return ()=>{
      isMounted = false;
    };
  }, []);

//Form submission handler function
const handleAddIdea = async (newIdeaPayload) =>{
  setIsSaving(true);
  setSaveError(null);
  setStatusMessage('');

  try{
    const saveIdea = await createIdea(newIdeaPayload);
    //Append returned idea object with its ID
    setIdeas((prevIdeas) =>[...prevIdeas,saveIdea]);
    setStatusMessage('Idea added successfully.');
  } catch(err){
    setSaveError(err.message || 'Failed to submit data to the server.');

  } finally{
    setIsSaving(false);
  }
};

return(
  <div className='app-container' >
    <header className="app-header">
      <h1>IDEAS BOARD</h1>
      <p>Share an idea for your community</p>
    </header>
    
    <div className='main-layout'>
      {/* Left Column: Data Grid Tracking */}
      <div calssName="lsit-column">
        {isLoading ?(
          <div className="status-message loading">Loading ideas...</div>
        ) : loadError ?(
          <div className="status-message error">
            ❌ Error Loading Data: {loadError} <br />
            <button className='retry-button' onClick={loadInitialIdeas}>
              Retry
            </button>
          </div>
          ) : (
            <IdeaList ideas={ideas} />
          )}
      </div>

      <div className="form-column">
        <IdeaForm onSubmit={handleAddIdea} isSaving={isSaving} />

        {saveError &&(
          <p className="status-message error">
            ❌ Error Saving: {saveError} (Form values preserved)
          </p>
        )}
      </div>
    </div>
    
    <footer className='app-footer'>
      {statusMessage && (
        <p className='status-region'>
          Status region: {statusMessage}
        </p>
      )}
    </footer>
  </div>
);


}

export default App;
