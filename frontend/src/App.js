import './App.css';
import React, {useState, useEffect, useCallback} from "react"
import axios from "axios";
import {useDropzone} from 'react-dropzone'

const UserProfiles = () => {
  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);
      setUserProfiles(res.data)
    });
  };
  useEffect(()=>{
    fetchUserProfiles();
  }, []);
  return userProfiles.map((userProfile, index) => {
    return (
        <div key={index}>
          {/*to do: profile image*/}
          <br/>
          <br/>
          <h1>{userProfile.username}</h1>
          <p>{userProfile.userProfileId}</p>
            <Dropzone {...userProfile}/>
            <br/>
        </div>
    );
  });
}

function Dropzone(userProfileId) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
    const formData = new FormData();
    formData.append("file", file);


console.log("userProfile Id: ",userProfileId);

      axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId.userProfileId}/image/upload`,
          formData,
          {
              headers: {
                  "Content-Type": "multipart/form-data"
              }
          }).then(()=> {
              console.log("file uploaded successfully")
      }).catch(err => {
          console.log(err);
      });
  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
      <div {...getRootProps()}>
        <input {...getInputProps()} />
        {
          isDragActive ?
              <p>Drop the image here ...</p> :
              <p>Drag 'n' drop some image, or click to select profile image</p>
        }
      </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfiles/>
    </div>
  );
}
export default App;
