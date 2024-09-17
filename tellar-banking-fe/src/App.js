import React from 'react';
import EmployeeList from './components/EmployeeList';
import EmployeeForm from './components/EmployeeForm';
import EmployeeBalance from './components/EmployeeBalance';
import Header from './components/Header';
import { EmployeeProvider } from './context/EmployeeState';  // Corrected import for EmployeeProvider

function App() {
  return (
    <EmployeeProvider>
      <div className="App">
        <Header />
        <div className="container">
          <EmployeeForm />
          <EmployeeBalance />
          <EmployeeList />
        </div>
      </div>
    </EmployeeProvider>
  );
}

export default App;
