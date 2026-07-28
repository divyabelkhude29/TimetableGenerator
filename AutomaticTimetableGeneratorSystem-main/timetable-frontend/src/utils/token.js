const TOKEN_KEY = "jwtToken";

const USER_KEY = "user";

export const setToken = (token) => {

    localStorage.setItem(TOKEN_KEY, token);

};

export const getToken = () => {

    return localStorage.getItem(TOKEN_KEY);

};

export const removeToken = () => {

    localStorage.removeItem(TOKEN_KEY);

    localStorage.removeItem(USER_KEY);

};

export const setUser = (user) => {

    localStorage.setItem(USER_KEY, JSON.stringify(user));

};

export const getUser = () => {

    const user = localStorage.getItem(USER_KEY);

    return user ? JSON.parse(user) : null;

};
export const getRole = () => {

    const user = getUser();

    return user?.role;

};