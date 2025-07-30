import { Navigate } from 'react-router-dom';

export default function PrivateRoute({ children }) {
  let role = localStorage.getItem('role');
  let permissions = localStorage.getItem('permissions');
  if(permissions){
    permissions = JSON.parse(permissions);
  }
  let pageName = children.type.name;
  
  if(!role){
    return pageName === 'LoginPage'
      ? children
      : <Navigate to="/login" replace />;
  }
  if (pageName === 'LoginPage') {
    return <Navigate to="/books" replace />;
  }
  if (pageName === 'BookFormPage' && !(permissions?.canAddBooks || permissions?.canUpdateBooks)) {
    return <Navigate to="/books" replace />;
  }

  return children;
}
