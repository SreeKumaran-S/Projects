import '../styling/common.css';
import '../styling/authors-page.css';
import NavbarComponent from './NavbarComponent';
import useAuthorsPage from '../hooks/authors-hook';

function AuthorsPage() {
    let {
        fetchLimit, currentPageNumber, isFirstPage,
        isLastPage, authorsData,
        handlePrevButtonClick, handleNextButtonClick,
        handleAuthorSearch
    } = useAuthorsPage();

    return (
        <div className="ui-page-wrapper ">
            <NavbarComponent />
            <div className="ui-search-author-container">
                <input className="ui-search-author-element" type="text" name="searchAuthor" onKeyUp={handleAuthorSearch} />
            </div>
            <div className="ui-data-header ui-flex">Authors Data</div>
                <>
                    <table className="ui-table">
                        <thead>
                            <tr>
                                <th>SL.NO</th>
                                <th>Author</th>
                            </tr>
                        </thead>
                        <tbody>
                        {(!authorsData || authorsData.authors.length === 0) ? (
                            <tr><td colSpan='10000' >No authors found</td></tr>
                        ) : (
                            authorsData.authors.map((author, index) => (
                                <tr key={index}>
                                    <td>{((currentPageNumber - 1) * fetchLimit) + (index + 1)}</td>
                                    <td>{author.name}</td>
                                </tr>
                            ))
                        )}
                    </tbody>
                    </table>
                    <div className="ui-footer ui-flex">
                        <span className="ui-footer-total ui-flex">
                            <label>Total authors :</label>
                            <span className="ui-footer-total-count">{authorsData?.totalAuthorsCount}</span>
                        </span>
                        <div className="ui-footer-buttons ui-flex">
                            <button className={`ui-button ${isFirstPage ? 'ui-btn-state-disabled' : ''}`} onClick={handlePrevButtonClick} >Prev</button>
                            <div className="ui-current-page-number">{currentPageNumber}</div>
                            <button className={`ui-button ${isLastPage ? 'ui-btn-state-disabled' : ''}`} onClick={handleNextButtonClick} >Next</button>
                        </div>
                    </div>
                </>
           
        </div>
    );
}

export default AuthorsPage;