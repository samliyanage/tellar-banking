import React, { useContext, useEffect } from 'react';
import EmployeeContext from '../context/EmployeeState';  

const EmployeeList = () => {
  const { employees, getEmployees } = useContext(EmployeeContext);  

  useEffect(() => {
    getEmployees();
  }, [getEmployees]);  

  return (
    <div>
      <h2>Employee List</h2>
      <ul>
        {employees.map((employee) => (
          <li key={employee.email}>
            {employee.name} - : ${employee.credit}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EmployeeList;
