import React, { useReducer } from 'react';
import EmployeeContext from './EmployeeContext';  // import the context
import api from '../services/api';

// Define the initial state and the reducer function
const initialState = {
  employees: [],
};

// Define the reducer function
const employeeReducer = (state, action) => {
  switch (action.type) {
    case 'GET_EMPLOYEES':
      return { ...state, employees: action.payload };
    case 'ADD_EMPLOYEE':
      return { ...state, employees: [...state.employees, action.payload] };
    default:
      return state;
  }
};

// EmployeeProvider function to provide the state and actions
export const EmployeeProvider = (props) => {
  const [state, dispatch] = useReducer(employeeReducer, initialState);

  // Action to fetch employees
  const getEmployees = async () => {
    try{
        const res = await api.get('/employees/all?company_name=Oracle');
        dispatch({ type: 'GET_EMPLOYEES', payload: res.data });
    } catch (error) {
        console.error('Failed to fetch employees:', error.message);
        alert('Network Error: Could not fetch employees');
    }
};

  // Action to add a new employee
  const addEmployee = async (employee) => {
    try{
        const res = await api.post('/employees', employee);
        dispatch({ type: 'ADD_EMPLOYEE', payload: res.data });
    } catch (error) {
        console.error('Failed add a new employee:', error.message);
        alert('Network Error: Could not add a new employee');
    }
};

  // Action to get employee balance
  const getEmployeeBalance = async (employee) => {
    try{
        const res = await api.post('/employees/credit-balance', employee);
        return res.data.credit;
    } catch (error) {
        console.error('Failed to get employee balance:', error.message);
        alert('Network Error: Could not get employee balance');
    }
  };

  return (
    <EmployeeContext.Provider
      value={{
        employees: state.employees,
        getEmployees,
        addEmployee,
        getEmployeeBalance,
      }}
    >
      {props.children}
    </EmployeeContext.Provider>
  );
};

// Export EmployeeContext so it can be used in other components
export default EmployeeContext;
