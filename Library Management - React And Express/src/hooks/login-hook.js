import { useState, useEffect } from 'react';
import { validateUser , getUserPermission} from '../service/requestHandler'; 
import { useNavigate } from 'react-router-dom';
import { useStatus } from './StatusContext';

export function useLoginForm() {
  // console.log(process.env.NODE_ENV);
  let { showStatus } = useStatus();
  let navigate = useNavigate();
  let [username, setUsername] = useState('');
  let [password, setPassword] = useState('');
  let [warning, setWarning] = useState({
    nameWarn:null,
    passWarn:null
  });

  
  useEffect(() => {
    if (localStorage.getItem('logoutStatusOnce') === 'true') {
      showStatus('You logged out Successfully', 'info');
      localStorage.setItem('logoutStatusOnce', 'false');
    }
  }, [showStatus]);

  let handleUsernameChange = (e) => setUsername(e.target.value);
  let handlePasswordChange = (e) => setPassword(e.target.value);

  let handleSubmit = (e) => {
    e.preventDefault();
    let userNameValid = username?.trim().length > 0;
    let passwordValid = password?.trim().length > 0;

    if(!userNameValid){
      setWarning({
        nameWarn:"Enter valid user name",
        passWarn:null
      })
    }
    else if(!passwordValid){
      setWarning({
        nameWarn:null,
        passWarn:"Enter valid password"
      })
    } 
    else{
    let callback = {
      success: (response) => {
        let data = response.data;
        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("username", data.username);

        getUserPermission(null, {
          success: (response) => {
            let data = response.data;
            localStorage.setItem("role", data.role);
            localStorage.setItem("permissions", JSON.stringify(data.permissions));
            setWarning({
              nameWarn:null,
              passWarn:null
            })
            navigate('/books');
          },
          error: () => {
          }
        });
        
      },
      error: (error) => {
        let errorCode = error.code;
        if(errorCode === 'USERNAME_NOT_FOUND'){
          setWarning({
            nameWarn:"Enter valid user name",
            passWarn:null
          })
        }
        else if(errorCode === 'WRONG_PASSWORD'){
          setWarning({
            nameWarn:null,
            passWarn:"Enter valid password"
          })
        }    
        showStatus('Invalid credentials', 'error');
      }
    }
    validateUser({username,password},callback);
   }
  };

  return {
    warning,
    handleUsernameChange,
    handlePasswordChange,
    handleSubmit
  };
}