import { navbars } from "../service/constants";
import '../styling/common.css';
import useLogout from "../hooks/logout-hook";

function NavbarComponent() {
    let logout = useLogout();
    let userRole = localStorage.getItem('role');
    return (
        <div className="ui-nav-bar ui-flex ui-books-nav-bar">
            <ul className="ui-nav-items ui-flex">
                {navbars.map((navItem, index) => (
                    !(navItem.label === 'Add Book' && userRole === 'guest') && (
                        <li className="ui-nav-item" key={index}>
                            <a href={navItem.url}>{navItem.label}</a>
                        </li>
                    )
                ))}
                <li className="ui-log-out-button ui-nav-item" onClick={(e)=> logout()}>
                     Log Out
                </li>
            </ul>
        </div>
    )
}
export default NavbarComponent;