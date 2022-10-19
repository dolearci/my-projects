import { Dialog, Transition } from "@headlessui/react";
import Image from "next/image";
import { React, useRef, Fragment, useState, useEffect } from "react";
import WoodWorkListManagement from "./WoodWorkListManagement";
import { Textarea } from "@material-tailwind/react";
import { AiOutlineClose } from "react-icons/ai";

const AddWoodWork = () => {
  const WOODWORK_API = "http://localhost:8080/woodwork";

  let [isOpen, setIsOpen] = useState(false);
  let hiddenFileInput = useRef(null);
  let [imgsSrc, setImgsSrc] = useState([]);
  let [image1, setImage1] = useState(null);
  let [image2, setImage2] = useState(null);
  let [image3, setImage3] = useState(null);
  let [image4, setImage4] = useState(null);
  let [woodWork, setWoodWork] = useState({
    id: "",
    description: "",
    type: "",
    image1: "",
    image2: "",
    image3: "",
    image4: "",
    color: "",
  });

  let [responseWoodWork, setResponseWoodWork] = useState({
    id: "",
    description: "",
    type: "",
    image1: "",
    image2: "",
    image3: "",
    image4: "",
    color: "",
  });

  const handleClick = () => {
    hiddenFileInput.current.click();
  };

  const addImage = (e) => {
    let counter = 0;
    for (const file of e.target.files) {
      counter = counter + 1;
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = (e) => {
        setImgsSrc((imgs) => [...imgs, e.target.result]);
      };
      reader.onerror = () => {
        console.log(reader.error);
      };
      if (counter == 1) setImage1(file.name);
      else if (counter == 2) setImage2(file.name);
      else if (counter == 3) setImage3(file.name);
      else if (counter == 4) {
        setImage4(file.name);
        break;
      }
    }
  }

  useEffect(() => {
    if (imgsSrc) {
      setWoodWork((prevState) => ({
        ...prevState,
        image1: imgsSrc[0],
      }));
      setWoodWork((prevState) => ({
        ...prevState,
        image2: imgsSrc[1],
      }));
      setWoodWork((prevState) => ({
        ...prevState,
        image3: imgsSrc[2],
      }));
      setWoodWork((prevState) => ({
        ...prevState,
        image4: imgsSrc[3],
      }));
    }
  }, [imgsSrc]);

  const removeImage = () => {
    setImage1(null);
    setImage2(null);
    setImage3(null);
    setImage4(null);
    setImgsSrc([]);
  };

  function closeModal() {
    setIsOpen(false);
  }

  function openModal() {
    setIsOpen(true);
  }

  const handleChange = (event) => {
    const value = event.target.value;
    setWoodWork({ ...woodWork, [event.target.name]: value });
  };

  const saveWoodWork = async (e) => {
    if (imgsSrc == null) return;
    e.preventDefault();
    console.log(JSON.stringify(woodWork));
    const response = await fetch(WOODWORK_API, {
      method: "POST",
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

  const reset = (e) => {
    e.preventDefault();
    setWoodWork({
      id: "",
      description: "",
      type: "",
      image1: "",
      image2: "",
      image3: "",
      image4: "",
      color: "",
    });
    setIsOpen(false);
    setImage1(null);
    setImage2(null);
    setImage3(null);
    setImage4(null);
    setImgsSrc([]);
  };

  return (
    <>
      <div className="container mx-auto my-6 text-center">
        <div className="h-12">
          <button
            onClick={openModal}
            className="rounded bg-green-500 hover:bg-green-600 text-white px-6 py-2 font-semibold"
          >
            Pridať novú prácu
          </button>
        </div>
      </div>
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
              <div className="inline-block w-full max-w-md p-6 my-8 overflow-hidden text-left align-middle transition-all mt-28 transform bg-white shadow-xl rounded-md">
                <Dialog.Title
                  as="h3"
                  className="text-lg font-medium leading-6 text-gray-900"
                >
                  Pridať novú prácu
                </Dialog.Title>
                <div className="flex max-w-md max-auto">
                  <div className="py-2">
                    <div className="h-32 my-4">
                      <label className="block text-gray-600 font-normal">
                        Krátky popis
                      </label>
                      <Textarea
                        type="text"
                        name="description"
                        rows="3"
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
                        className="file:block h-10 w-96 border mt-2 px-2 py-2"
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
                        className="text-md h-10 w-96 border mt-2 px-2 py-2"
                      >
                        <option selected>{woodWork.type}</option>
                        <option value="Kuchyňa">Kuchyňa</option>
                        <option value="Izba">Izba</option>
                        <option value="Skriňa">Skriňa</option>
                        <option value="Chodba">Chodba</option>
                      </select>
                    </div>
                    <div className="h-48 my-5">
                      <label className="block text-gray-600 font-normal">
                        Obrázky
                      </label>
                      <button
                        onClick={handleClick}
                        type="text"
                        name="image"
                        className="rounded text-white font-semibold bg-blue-400 mt-2 hover:bg-blue-700 py-2 px-6"
                      >
                        Pridať obrázky
                      </button>
                      {image1 && (
                        <div className="mt-2 space-y-2 items-center justify-center">
                          <p className="text-xs">{image1}</p>

                          {image2 && <p className="text-xs">{image2}</p>}
                          {image3 && <p className="text-xs">{image3}</p>}
                          {image4 && <p className="text-xs">{image4}</p>}
                          <AiOutlineClose
                            className="rounded-full shadow-lg p-3 cursor-pointer"
                            size={40}
                            onClick={removeImage}
                          ></AiOutlineClose>
                        </div>
                      )}
                      <input
                        ref={hiddenFileInput}
                        onChange={addImage}
                        name="image"
                        type="file"
                        accept="image/*"
                        hidden
                        multiple
                      />
                    </div>
                    <div className="h-8 my-4 space-x-4 pt-4">
                      <button
                        onClick={saveWoodWork}
                        className="rounded text-white font-semibold bg-green-500 hover:bg-green-700 py-2 px-6"
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
      <WoodWorkListManagement woodWork={responseWoodWork} />
    </>
  );
};

export default AddWoodWork;
