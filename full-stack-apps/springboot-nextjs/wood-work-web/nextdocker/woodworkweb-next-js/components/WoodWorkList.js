import React, { useState, useEffect } from "react";
import WoodWorkBody from "./WoodWorkBody";

const WoodWorkList = ({ woodworks, loading }) => {
  return (
    <div className="flex flex-col">
      <div className="flex">
        <table className="w-full">
          <thead>
            <tr>
              <th className="sticky top-0 text-center font-medium text-gray-800 bg-gray-200 uppercase tracking-wide py-3 px-6">
                Obrázky
              </th>
              <th className="sticky top-0 text-center font-medium text-gray-800 bg-gray-200 uppercase tracking-wide py-3 px-6">
                Popis
              </th>
            </tr>
          </thead>
          {!loading && (
            <tbody className="bg-gray-50">
              {woodworks?.map((woodwork) => (
                <WoodWorkBody
                  woodwork={woodwork}
                  key={woodwork.id}
                ></WoodWorkBody>
              ))}
            </tbody>
          )}
        </table>
      </div>
    </div>
  );
};

export default WoodWorkList;
