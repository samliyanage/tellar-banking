import React, { useState, useContext } from 'react';
import EmployeeContext from '../context/EmployeeState';

const EmployeeBalance = () => {
  const { getEmployeeBalance } = useContext(EmployeeContext);
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [company, setCompany] = useState('');
  const [balance, setBalance] = useState(null);

  const checkBalance = async (e) => {
    e.preventDefault();
    if (email && name && company) {
      const balance = await getEmployeeBalance({ email, company, name });
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
          placeholder="Employee Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
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
