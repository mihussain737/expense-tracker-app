import React from "react";
import { Link, useLocation } from "react-router-dom";

const Navbar = () => {
  const path = useLocation().pathname;
  return (
    <div className="h-[50px] bg-blue-800 text-white z-40 flex items-center sticky">
      <div className="lg:px-14 sm:px-8 px-4 w-full justify-between">
        <ul className="flex items-center justify-around">
          <li
            className={`${path === "/" ? "text-slate-500 font-semibold" : "text-white"}`}
          >
            <Link to="/">Home</Link>
          </li>
          <li
            className={`${path === "/expense" ? "text-slate-500 font-semibold" : "text-white"}`}
            
          >
            <Link to="/expense">Expense</Link>
          </li>
          <li>
            <Link>Reports</Link>
          </li>
          <li>
            <Link>Admin</Link>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default Navbar;
