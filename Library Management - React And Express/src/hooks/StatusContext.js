import { createContext, useContext } from 'react';

export const StatusContext = createContext();
export const useStatus = () => useContext(StatusContext);
