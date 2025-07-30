import { useNavigate } from 'react-router-dom';
import {logOutUser} from '../service/requestHandler';

export default function useLogout() {
    let navigate = useNavigate();
    return function logout() {
      let callback = {
        success:((resp)=>{
          localStorage.clear();
          localStorage.setItem('logoutStatusOnce', true);
          navigate('/');
        }),
        error:(()=>{
          
        })
      }
      logOutUser({},callback);   
    };
}
  