// ResourceDetail3.jsx
import React from 'react';

const ResourceDetail3 = ({ setImage }) => {
  const handlePhotoChange = (event) => {
    const file = event.target.files[0];

    if (file) {
      setImage(file)
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
