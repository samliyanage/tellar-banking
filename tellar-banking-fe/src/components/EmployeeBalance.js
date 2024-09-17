import React, { useState, useContext } from 'react';
import EmployeeContext from '../context/EmployeeState';  // Correct import from EmployeeState

const EmployeeBalance = () => {
  const { getEmployeeBalance } = useContext(EmployeeContext);  // Use EmployeeContext here
  const [email, setEmail] = useState('');
  const [company, setCompany] = useState('');
  const [balance, setBalance] = useState(null);

  const checkBalance = async (e) => {
    e.preventDefault();
    if (email && company) {
      const balance = await getEmployeeBalance({ email, company });
      setBalance(balance);
    }
  };

  return (
    <div>
      <h2>Check Employee Balance</h2>
      <form onSubmit={checkBalance}>
        <input
          type="text"
          placeholder="Employee Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="text"
          placeholder="Company"
          value={company}
          onChange={(e) => setCompany(e.target.value)}
        />
        <button type="submit">Check Balance</button>
      </form>
      {balance !== null && <p>Balance: ${balance}</p>}
    </div>
  );
};

export default EmployeeBalance;
