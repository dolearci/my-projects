import React, { useState } from "react";
import Link from 'next/link';
import { GiWoodPile } from "react-icons/gi";
import { FaFacebook, FaInstagram, FaDiscord } from "react-icons/fa";
import { FcSearch } from "react-icons/fc";
import { AiOutlineClose, AiOutlineMail, AiOutlineMenu } from "react-icons/ai";
import SoupKitchenIcon from "@mui/icons-material/SoupKitchen";
import BedRoundedIcon from "@mui/icons-material/BedRounded";
import CheckroomIcon from "@mui/icons-material/Checkroom";
import DoorSlidingOutlinedIcon from "@mui/icons-material/DoorSlidingOutlined";
import HouseSidingIcon from "@mui/icons-material/HouseSiding";

const Header = ({ query, onQueryChange, onTypeChange }) => {
  const [nav, setNav] = useState(false);
  const handleNav = () => {
    setNav(!nav);
  };

  return (
    <div className="bg-gray-50 items-center space-x-3 flex p-2 pr-4 shadow-lg top-0 sticky z-50 h-24">
      {/* Left */}
      <Link 
        href="/"
        className="items-center hidden lg:inline-flex justify-start py-2 space-x-1 pr-2"
      >
        <GiWoodPile size={40}></GiWoodPile>
        <p className="font-bold text-base">Stolárstvo Oli</p>
      </Link>
      {/* Middle */}
      <div className="flex py-2 px-2 flex-grow items-center justify-center space-x-2 rounded-full hover:scale-y-110 ease-in duration-300 bg-gray-200">
        <FcSearch size={20} />
        <input
          className="bg-transparent focus:outline-none"
          type="text"
          name="query"
          id="query"
          value={query}
          placeholder="typ, farba, číslo ..."
          onChange={(event) => {
            onQueryChange(event.target.value);
          }}
          onClick={() => onTypeChange("")}
        ></input>
      </div>
      {/* Right */}
      <div className="items-center justify-center space-x-3 inline-flex">
        <button
          className="bg-gray-200 hidden  lg:inline-flex rounded-md hover:bg-gray-300 focus:bg-slate-400 hover:scale-105 ease-in duration-300 focus:shadow-lg cursor-pointer px-6 py-2"
          onClick={() => onTypeChange("")}
        >
          <HouseSidingIcon size={20}></HouseSidingIcon>
        </button>
        <button
          className="bg-gray-200 flex-grow hidden lg:inline-flex justify-center rounded-md hover:bg-gray-300 hover:scale-105 ease-in duration-300 focus:bg-slate-400 focus:shadow-lg cursor-pointer px-4 py-2"
          onClick={() => onTypeChange("Kuchyňa")}
        >
          <SoupKitchenIcon size={20}></SoupKitchenIcon>
          Kuchyne
        </button>
        <button
          className="bg-gray-200 flex-grow hidden lg:inline-flex justify-center rounded-md hover:bg-gray-300 hover:scale-105 ease-in duration-300 focus:bg-slate-400 backdrop:focus:shadow-lg cursor-pointer px-4 py-2"
          onClick={() => onTypeChange("Izba")}
        >
          <BedRoundedIcon size={20}></BedRoundedIcon>
          Izby
        </button>
        <button
          className="bg-gray-200 flex-grow hidden lg:inline-flex justify-center rounded-md hover:bg-gray-300 hover:scale-105 ease-in duration-300 focus:bg-slate-400 focus:shadow-lg cursor-pointer px-4 py-2"
          onClick={() => onTypeChange("Chodba")}
        >
          <CheckroomIcon size={20}></CheckroomIcon>
          Chodby
        </button>
        <button
          className="bg-gray-200 flex-grow hidden lg:inline-flex justify-center rounded-md hover:bg-gray-300 hover:scale-105 ease-in duration-300 focus:bg-slate-400 focus:shadow-lg cursor-pointer px-4 py-2"
          onClick={() => onTypeChange("Skriňa")}
        >
          <DoorSlidingOutlinedIcon size={20}></DoorSlidingOutlinedIcon>
          Skrine
        </button>
        <button
          onClick={handleNav}
          className="bg-gray-200 rounded-md hover:bg-gray-300 hover:scale-105 ease-in duration-300 cursor-pointer px-6 py-2"
        >
          <AiOutlineMenu size={25}></AiOutlineMenu>
        </button>
      </div>
      <div
        className={
          nav
            ? " fixed left-0 top-0 w-[75%] sm:w-[60%] md:w-[45%] h-screen bg-[#ecf0f3] p-10 ease-in duration-500"
            : "fixed left-[-100%] top-0 p-10 ease-in duration-500"
        }
      >
        <div>
          <div className="flex w-full items-center justify-between">
            <GiWoodPile size={30} />
            <div
              onClick={handleNav}
              className="rounded-full shadow-lg shadow-gray-400 p-3 cursor-pointer"
            >
              <AiOutlineClose />
            </div>
          </div>
          <div className="border-b border-gray-300 my-4">
            <p className="w-[85%] md:w-[90%] py-4">Kontakt</p>
          </div>
        </div>
        <div className="py-4 flex flex-col">
          <ul className="uppercase">
            <li onClick={() => setNav(false)} className="py-4 text-sm">
              Názov firmy: Stolárstvo Oli
            </li>
            <li onClick={() => setNav(false)} className="py-4 text-sm">
              Majiteľ: Miloš Olearčin
            </li>
            <li onClick={() => setNav(false)} className="py-4 text-sm">
              Adresa dielne: Beloveža 287
            </li>
            <li onClick={() => setNav(false)} className="py-4 text-sm">
              Telefónne číslo: +421 999 999 999
            </li>
            <li onClick={() => setNav(false)} className="py-4 text-sm">
              Email: olearcinmilos@centrum.sk
            </li>
          </ul>
          <div className="pt-16">
            <p className="uppercase tracking-widest">Sociálne siete</p>
            <div className="inline-flex pt-2 items-center space-x-4 first-line:my-4 w-full sm:w-[80%]">
              <a
                href="https://www.facebook.com/milos.olearcin"
                target="_blank"
                rel="noreferrer"
              >
                <div className="rounded-full inline-flex items-center shadow-lg space-x-2 shadow-gray-400 p-3 cursor-pointer hover:scale-105 ease-in duration-300">
                  <FaFacebook size={20} className="text-blue-600" />
                  <p className="hidden lg:inline-flex">Facebook</p>
                </div>
              </a>
              <a
                href="https://www.instagram.com/dano_olko/"
                target="_blank"
                rel="noreferrer"
              >
                <div className="rounded-full inline-flex items-center shadow-lg space-x-2 shadow-gray-400 p-3 cursor-pointer hover:scale-105 ease-in duration-300">
                  <FaInstagram size={20} className="text-purple-600" />
                  <p className="hidden lg:inline-flex">Instagram</p>
                </div>
              </a>
              <a
                href="https://discordapp.com/users/olilik#2199"
                target="_blank"
                rel="noreferrer"
              >
                <div className="rounded-full inline-flex items-center shadow-lg space-x-2 shadow-gray-400 p-3 cursor-pointer hover:scale-105 ease-in duration-300">
                  <FaDiscord size={20} className="text-blue-600" />
                  <p className="hidden lg:inline-flex">Discord</p>
                </div>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Header;
