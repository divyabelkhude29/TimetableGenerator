import { useState, useEffect } from 'react';
import { getUser } from '../utils/token';

const useAuth = () => {
   const [user, setUser] = useState(null);
   const [loading, setLoading] = useState(true);

   useEffect(() => {
      setUser(getUser());
      setLoading(false);
   }, []);

   const logout = () => {
      localStorage.clear();
      setUser(null);
   };

   return {
      user,
      loading,
      logout,
   };
};

export default useAuth;