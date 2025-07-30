import "../styling/select-component.css"

function SelectComponent({ authorNames = [],isAuthorDialogOpen,handleAuthorSearch ,handleSelectAuthor}) {
    if (!authorNames ){
        return (<div></div>);
    }
    else if(authorNames.length <= 0) {
        return (<div></div>);
    }
    else {
        return (
            <div className={`ui-select-container ${isAuthorDialogOpen ? '': 'display-none'}`}>
                <input className="ui-select-search" onKeyUp={handleAuthorSearch} />
                <div className="ui-selectable-list">
                    <ul className="ui-selectable-list-container">
                        {authorNames.authors.map((author, index) => (
                            <li key={index} className="ui-selectable-item" onClick={handleSelectAuthor}>{author.name}</li>
                        ))}
                    </ul>
                </div>
            </div>
        );
    }
}
export default SelectComponent;