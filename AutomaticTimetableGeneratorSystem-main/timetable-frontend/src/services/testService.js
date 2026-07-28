import api from "../api/api";

export const pingBackend = async () => {
  const response = await api.get("/actuator/health");
  return response.data;
};