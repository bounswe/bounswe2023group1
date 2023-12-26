import React, { createContext, useState } from 'react';

export const NeedContext = createContext();

export const NeedProvider = ({ children }) => {
    const [needs, setNeeds] = useState([]);

    const addNeed = (newNeed) => {
        setNeeds((prevNeeds) => [...prevNeeds, newNeed]);
    };

    const updateState = (index, newData) => {
        return new Promise((resolve) => {
            setNeeds((prevNeeds) => {
                const newNeeds = [...prevNeeds];
                newNeeds[index] = { ...newNeeds[index], ...newData };
                resolve(newNeeds);
                return newNeeds;
            });
        });
    };

    const resetNeeds = () => {
        setNeeds([]);
    };

    return (
        <NeedContext.Provider value={{ needs, addNeed, updateState, resetNeeds }}>
            {children}
        </NeedContext.Provider>
    );
};
