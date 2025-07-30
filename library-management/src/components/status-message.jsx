import { useStatus } from '../hooks/StatusContext';
import '../styling/status-message.css';

let StatusMessage = () => {
  let { status } = useStatus();

  if (!status.message) {
    return null;
  }

  return (
    <div className={`status-message ${status.type}`}>
      {status.message}
    </div>
  );
};

export default StatusMessage;
