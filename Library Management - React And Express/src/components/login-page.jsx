import '../styling/login-page.css';
import '../styling/common.css';
import { useLoginForm } from '../hooks/login-hook';

function LoginPage() {
    const {
        warning,
        handleUsernameChange,
        handlePasswordChange,
        handleSubmit
    } = useLoginForm();
    return (
        <div className="ui-login-page ui-flex" id="loginPage">
            <span className="ui-login-title">LOGIN PAGE</span>
            <form className="ui-login-form ui-flex"  onSubmit={handleSubmit}>
                <input
                    className="ui-login-name"
                    id="loginName"
                    type="text"
                    placeholder="Username"
                    onChange={handleUsernameChange}
                    
                />
                {warning?.nameWarn && (
                    <span className="ui-invalid-value">
                        {warning.nameWarn}
                    </span>
                )}
                <input
                    className="ui-login-password"
                    id="loginPassword"
                    type="password"
                    placeholder="Password"
                    onChange={handlePasswordChange}
                    
                />
                 {warning?.passWarn && (
                    <span className="ui-invalid-value">
                        {warning.passWarn}
                    </span>
                 )}
                <button
                    className="ui-login-button"
                    id="loginBtn"
                    type="submit"
                >
                    Login
                </button>
            </form>
        </div>
    );
}

export default LoginPage;