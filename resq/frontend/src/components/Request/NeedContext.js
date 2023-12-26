import React, { createContext, useState, useContext } from 'react';
import PropTypes from 'prop-types'; // Corrected import for PropTypes

export const NeedContext = createContext();

export const useNeed = () => useContext(NeedContext);

export const NeedProvider = ({ children }) => {
    const [needData, setNeedData] = useState({});

    const updateNeedData = (newData) => {
        setNeedData((prevData) => ({ ...prevData, ...newData }));
    };

    return (
        <NeedContext.Provider value={{ needData, updateNeedData }}>
            {children}
        </NeedContext.Provider>
    );
};

NeedProvider.propTypes = {
    children: PropTypes.node.isRequired,
};
