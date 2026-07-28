import axiosInstance from "../api/axiosInstance";


const login = async (username, password) => {

  const response = await axiosInstance.post(
    "/auth/login",
    {
      username: username,
      password: password,
    }
  );

  return response.data;
};


const register = async (registerData) => {

  const response = await axiosInstance.post(
    "/auth/register",
    registerData
  );

  return response.data;
};


export default {
  login,
  register,
};