import React from 'react'
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Navbar from './components/shared/Navbar'
import Home from './components/shared/Home'
import Expense from './components/payload/Expense'

const App = () => {
  return (
    <BrowserRouter>
      <Navbar/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/expense" element={<Expense/>}/>
      </Routes>
    </BrowserRouter>
  )
}

export default App