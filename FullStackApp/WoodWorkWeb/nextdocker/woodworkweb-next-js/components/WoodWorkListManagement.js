import React, { useEffect, useState } from "react";
import EditWoodWork from "./EditWoodWork";
import WoodWorkBodyManagement from "./WoodWorkBodyManagement";
import { FcSearch } from "react-icons/fc";

const WoodWorkListManagement = ({ woodWork }) => {
  const WOODWORK_API = "http://localhost:8080/woodwork";
  const [woodworks, setWoodWorks] = useState(null);
  const [loading, setLoading] = useState(true);
  const [woodWorkId, setWoodWorkId] = useState(null);
  const [responseWoodWork, setResponseWoodWork] = useState(null);
  const [query, setQuery] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await fetch(WOODWORK_API, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });
        const woodworks = await response.json();
        setWoodWorks(woodworks);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, [woodWork, responseWoodWork]);

  const deleteWoodWork = (e, id) => {
    e.preventDefault();
    fetch(WOODWORK_API + "/" + id, {
      method: "DELETE",
    }).then((res) => {
      if (woodworks) {
        setWoodWorks((prevElement) => {
          return prevElement.filter((woodwork) => woodwork.id !== id);
        });
      }
    });
  };

  const editWoodWork = (e, id) => {
    e.preventDefault();
    setWoodWorkId(id);
  };

  const setFuncQuery = (query) => {
    setQuery(query);
  };

  return (
    <>
      <div className="flex py-2 px-2 flex-grow items-center justify-center space-x-2 rounded-full bg-gray-200">
        <FcSearch size={20} />
        <input
          className="bg-transparent focus:outline-none"
          type="text"
          name="query"
          id="query"
          value={query}
          placeholder="typ, farba, číslo ..."
          onChange={(event) => setFuncQuery(event.target.value)}
        ></input>
      </div>
      <div className="flex flex-col">
        <div className="flex">
          <table className="relative w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="text-center sticky top-0 font-medium text-gray-700 bg-gray-50 uppercase tracking-wide py-3 px-6">
                  Obrázok
                </th>
                <th className="text-center sticky top-0 font-medium text-gray-700 bg-gray-50 uppercase tracking-wide py-3 px-6">
                  Popis
                </th>
                <th className="text-center sticky top-0 font-medium text-gray-700 bg-gray-50 uppercase tracking-wide py-3 px-6">
                  Číslo
                </th>
                <th className="text-center sticky top-0 font-medium text-gray-700 bg-gray-50 uppercase tracking-wide py-3 px-6">
                  Typ práce
                </th>
                <th className="text-center sticky top-0 font-medium text-gray-700 bg-gray-50 uppercase tracking-wide py-3 px-6">
                  Farba
                </th>
                <th className="text-center sticky top-0 font-medium text-gray-700 bg-gray-50 uppercase tracking-wide py-3 px-6">
                  Spravovanie
                </th>
              </tr>
            </thead>
            {!loading && (
              <tbody className="bg-white">
                {woodworks
                  .filter((item) => {
                    return (
                      item.type.toLowerCase().includes(query.toLowerCase()) ||
                      item.description
                        .toLowerCase()
                        .includes(query.toLowerCase()) ||
                      item.color.toLowerCase().includes(query.toLowerCase()) ||
                      item.id.toString().includes(query.toLowerCase())
                    );
                  })
                  ?.map((woodwork) => (
                    <WoodWorkBodyManagement
                      woodwork={woodwork}
                      key={woodwork.id}
                      deleteWoodWork={deleteWoodWork}
                      editWoodWork={editWoodWork}
                    ></WoodWorkBodyManagement>
                  ))}
              </tbody>
            )}
          </table>
        </div>
      </div>
      <EditWoodWork
        woodWorkId={woodWorkId}
        setResponseWoodWork={setResponseWoodWork}
      />
    </>
  );
};

export default WoodWorkListManagement;
