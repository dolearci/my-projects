import { Dialog, Transition } from "@headlessui/react";
import { React, useState, useEffect, Fragment } from "react";
import { Textarea } from "@material-tailwind/react";

const EditWoodWork = ({ woodWorkId, setResponseWoodWork }) => {
  const WOODWORK_API = "http://localhost:8080/woodwork";

  const [isOpen, setIsOpen] = useState(false);
  const [woodWork, setWoodWork] = useState({
    id: "",
    description: "",
    type: "",
    color: "",
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(WOODWORK_API + "/" + woodWorkId, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });
        const _woodWork = await response.json();
        setWoodWork(_woodWork);
        setIsOpen(true);
      } catch (error) {
        console.log(error);
      }
    };
    if (woodWorkId) {
      fetchData();
    }
  }, [woodWorkId]);

  function closeModal() {
    setIsOpen(false);
  }

  const handleChange = (event) => {
    const value = event.target.value;
    setWoodWork({ ...woodWork, [event.target.name]: value });
  };

  const reset = (e) => {
    e.preventDefault();
    setIsOpen(false);
  };

  const updateWoodWork = async (e) => {
    e.preventDefault();
    const response = await fetch(WOODWORK_API + "/" + woodWorkId, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(woodWork),
    });
    if (!response.ok) {
      throw new Error("Something went wrong");
    }
    const _woodWork = await response.json();
    setResponseWoodWork(_woodWork);
    reset(e);
  };

  return (
    <Transition appear show={isOpen} as={Fragment}>
      <Dialog
        as="div"
        className="fixed inset-0 z-10 overflow-y-auto"
        onClose={closeModal}
      >
        <div className="min-h-screen px-4 text-center">
          <Transition.Child
            as={Fragment}
            enter="ease-out duration-300"
            enterFrom="opacity-0 scale-95"
            enterTo="opacity-100 scale-100"
            leave="ease-in duration-200"
            leaveFrom="opacity-100 scale-100"
            leaveTo="opacity-0 scale-95"
          >
            <div className="inline-block mt-28 w-full max-w-md p-6 my-8 overflow-hidden text-left align-middle transition-all transform bg-white shadow-2xl rounded-3xl">
              <Dialog.Title
                as="h3"
                className="text-lg font-medium leading-6 text-gray-900"
              >
                Upraviť prácu
              </Dialog.Title>
              <div className="flex m-w-fit max-auto">
                <div className="py-2">
                  <div className="h-32 my-4">
                    <label className="block text-gray-600 font-normal">
                      Krátky popis
                    </label>
                    <Textarea
                      type="text"
                      name="description"
                      value={woodWork.description}
                      onChange={(e) => handleChange(e)}
                      className="file:block h-28 w-96 border mt-2 px-2 py-2"
                    ></Textarea>
                  </div>
                  <div className="h-16 my-4">
                      <label className="block text-gray-600 font-normal">
                        Farba
                      </label>
                      <input
                        type="text"
                        name="color"
                        value={woodWork.color}
                        onChange={(e) => handleChange(e)}
                        className="text-lg file:block h-10 w-96 border mt-2 px-2 py-2"
                      ></input>
                    </div>
                  <div className="h-16 my-4">
                    <label className="block text-gray-600 font-normal">
                      Typ
                    </label>
                    <select
                      name="type"
                      value={woodWork.type}
                      onChange={(e) => handleChange(e)}
                      className="h-10 w-96 border mt-2 px-2 py-2"
                    >
                      <option selected>{woodWork.type}</option>
                      <option value="Kuchyňa">Kuchyňa</option>
                      <option value="Izba">Izba</option>
                      <option value="Skriňa">Skriňa</option>
                      <option value="Chodba">Chodba</option>
                    </select>
                  </div>
                  <div className="h-8 my-4 space-x-4 pt-4">
                    <button
                      onClick={updateWoodWork}
                      className="rounded text-white font-semibold bg-green-400 hover:bg-green-700 py-2 px-6"
                    >
                      Uložiť
                    </button>
                    <button
                      onClick={reset}
                      className="rounded text-white font-semibold bg-red-400 hover:bg-red-700 py-2 px-6"
                    >
                      Zatvoriť
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </Transition.Child>
        </div>
      </Dialog>
    </Transition>
  );
};

export default EditWoodWork;
