import React from "react";
import Image from "next/image";

const WoodWorkBodyManagement = ({ woodwork, deleteWoodWork, editWoodWork }) => {
  return (
    <tr key={woodwork.id}>
      <td className="flex py-4 shadow items-center justify-center">
        <Image src={woodwork.image1} height={200} width={300} alt="ahojky" />
      </td>
      <td className="shadow">
        <p className="min-w-full px-14 text-center font-medium tracking-wide white-space: nowrap w-60 text-gray-800">
          {woodwork.description}
        </p>
      </td>
      <td className="shadow">
        <p className="min-w-fit px-6 text-center font-medium tracking-wide white-space: nowrap text-gray-800">
          {woodwork.id}
        </p>
      </td>
      <td className="shadow">
        <p className="min-w-fit px-10 text-center font-medium tracking-wide white-space: nowrap text-gray-800">
          {woodwork.type}
        </p>
      </td>
      <td className="shadow">
        <p className="min-w-fit px-6 text-center font-medium tracking-wide white-space: nowrap text-gray-800">
          {woodwork.color}
        </p>
      </td>
      <td className="shadow text-center">
        <button
          type="button"
          onClick={(e) => editWoodWork(e, woodwork.id)}
          className="px-6 py-2.5 bg-blue-600 text-white font-medium text-xs uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
        >
          Upraviť
        </button>
        <button
          type="button"
          onClick={(e) => deleteWoodWork(e, woodwork.id)}
          className="px-6 py-2.5 bg-red-600 text-white font-medium text-xs uppercase rounded shadow-md hover:bg-red-700 hover:shadow-lg focus:bg-red-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-red-800 active:shadow-lg transition duration-150 ease-in-out"
        >
          Vymazať
        </button>
      </td>
    </tr>
  );
};

export default WoodWorkBodyManagement;
