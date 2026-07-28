import { createContext, useEffect, useState } from "react";

import {
    getToken,
    setToken,
    removeToken,
    setUser as saveUser,
    getUser
} from "../utils/token";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const [user, setUser] = useState(null);

    const [loading, setLoading] = useState(true);

    useEffect(() => {

        const token = getToken();

        const savedUser = getUser();

        if (token && savedUser) {

            setUser({
                ...savedUser,
                token
            });

        }

        setLoading(false);

    }, []);

    const login = (token, userData) => {

        setToken(token);

        saveUser(userData);

        setUser({
            ...userData,
            token
        });

    };

    const logout = () => {

        removeToken();

        setUser(null);

    };

    return (

        <AuthContext.Provider

            value={{

                user,

                loading,

                login,

                logout,

                isAuthenticated: !!user

            }}

        >

            {children}

        </AuthContext.Provider>

    );

};