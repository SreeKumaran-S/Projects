import "../styling/error-page.css"

function ErrorPage() {
    return (
        <div className="ui-error-container">
            <div>
                <h1>The possible reasons could be</h1>
                <ul>
                    <li>The URL you're trying to access might be invalid</li>
                    <li>Your access to this page might have been revoked</li>
                </ul>
                <p className="ui-hyperlink">
                    <span>Access Login Page</span>
                    <a href="/login">Login</a>
                </p>
            </div>
        </div>
    );
}
export default ErrorPage;