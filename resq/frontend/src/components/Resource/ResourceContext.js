import React, { createContext, useState, useContext } from 'react';

const ResourceContext = createContext();

export const useResource = () => useContext(ResourceContext);

export const ResourceProvider = ({ children }) => {
    const [resourceData, setResourceData] = useState({});

    const updateResourceData = (newData) => {
        setResourceData(prev => ({ ...prev, ...newData }));
    };

    return (
        <ResourceContext.Provider value={{ resourceData, updateResourceData }}>
            {children}
        </ResourceContext.Provider>
    );
};
