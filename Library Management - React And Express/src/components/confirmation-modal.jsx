function ConfirmationModal({message , handleCancel, handleConfirm}){
    return (   
        <>
        <div className="ui-overlay"></div>
        <div className="ui-confirmation-modal ui-flex">
          <p className="ui-confirmation-text">{message}</p>
          <div className="ui-confirmation-button-controls">
            <button onClick={handleCancel}>Cancel</button>
            <button onClick={handleConfirm}>Confirm</button>
          </div>
        </div>
        </> 
    )
}
export default ConfirmationModal;