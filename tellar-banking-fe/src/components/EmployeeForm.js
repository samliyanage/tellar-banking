import React, { useState, useContext } from 'react';
import EmployeeContext from '../context/EmployeeState'; 

const EmployeeForm = () => {
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [company, setCompany] = useState('');
  const { addEmployee } = useContext(EmployeeContext); 

  const onSubmit = (e) => {
    e.preventDefault();
    if (email && name && company) {
      addEmployee({ email, name, company });
      setEmail('');
      setName('');
      setCompany('');
    }
  };

  return (
    <form onSubmit={onSubmit}>
      <h2>Register Employee</h2>
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
      <button type="submit">Register Employee</button>
    </form>
  );
};

export default EmployeeForm;
