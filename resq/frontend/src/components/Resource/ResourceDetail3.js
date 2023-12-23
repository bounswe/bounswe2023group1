// ResourceDetail3.jsx
import React from 'react';

const ResourceDetail3 = ({ resourceData, setResourceData }) => {
  const handlePhotoChange = (event) => {
    const file = event.target.files[0];

    if (file) {
      setResourceData((prevData) => ({
        ...prevData,
        photo: file,
      }));
    }
  };

  return (
    <div>
      <h3>Upload a Photo</h3>
      <input type="file" accept="image/*" onChange={handlePhotoChange} />
    </div>
  );
};

export default ResourceDetail3;
