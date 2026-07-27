import React from "react";

import { FaSearch } from "react-icons/fa";
import "./SearchBar.css";

export const SearchBar = ({ onSearch }) => {
    const handleInputChange = (e) => {
        if (onSearch) {
            onSearch(e.target.value);
        }
    };

    return (
        <div className="input_cont">
            <FaSearch id="search_icon" />
            <input 
                placeholder="Buscar plan de estudios" 
                onChange={handleInputChange}
            />
        </div>
    );
}