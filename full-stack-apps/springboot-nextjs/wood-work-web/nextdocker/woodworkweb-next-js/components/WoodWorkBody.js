import { React, useState, Fragment } from "react";
import Image from "next/image";
import { Dialog, Transition } from "@headlessui/react";
import { AiOutlineClose } from "react-icons/ai";

const WoodWorkBody = ({ woodwork }) => {
  function closeModal() {
    setIsOpen(false);
  }

  const openModal = (e) => {
    setImage(e.target.src);
    setIsOpen(true);
  }
  
  let [isOpen, setIsOpen] = useState(false);
  let [image, setImage] = useState("");

  return (
    <>
      <tr key={woodwork.id} className="shadow-md">
        <td className="py-4 shadow-md">
          <div className="flex space-x-6 items-center justify-center pr-4 pl-4 ">
            <div className="flex">
              <Image
                className="hover:scale-110 pt-3 pb-3"
                src={woodwork.image1}
                onClick={(e) => openModal(e)}
                height={300}
                width={350}
                alt="ahojky"
              />
            </div>
            {woodwork.image2 && (
              <div className="hidden lg:flex">
                <Image
                  className="hover:scale-110 pt-3 pb-3"
                  src={woodwork.image2}
                  onClick={(e) => openModal(e)}
                  height={300}
                  width={350}
                  alt="ahojky"
                />
              </div>
            )}
            {woodwork.image3 && (
              <div className="hidden lg:flex">
                <Image
                  className="hover:scale-110 pt-3 pb-3"
                  src={woodwork.image3}
                  onClick={(e) => openModal(e)}
                  height={300}
                  width={350}
                  alt="ahojky"
                />
              </div>
            )}
            {woodwork.image4 && (
              <div className="hidden lg:flex">
                <Image
                  className="hover:scale-110 pt-3 pb-3"
                  src={woodwork.image4}
                  onClick={(e) => openModal(e)}
                  height={300}
                  width={350}
                  alt="ahojky"
                />
              </div>
            )}
          </div>
        </td>
        <td className="shadow-md">
          <p className="min-w-full text-center text-sm p-3 lg:text-lg tracking-wide white-space: nowrap text-gray-800 w-60 lg:w-96 ">
            <b className="text-xs lg:text-lg">Farba:</b> <br></br>{" "}
            {woodwork.color}
            <br></br>
            <b className="text-xs lg:text-lg">Krátky popis:</b> <br></br>{" "}
            {woodwork.description}
            <br></br>
            <b className="text-xs lg:text-lg">Identifikačné číslo:</b>{" "}
            {woodwork.id}
          </p>
        </td>
      </tr>
      <Transition appear show={isOpen} as={Fragment}>
        <Dialog
          as="div"
          className="fixed overflow-y-auto inset-0 z-10"
          onClose={closeModal}
          onClick={closeModal}
        >
          <div className="text-center">
            <Transition.Child
              as={Fragment}
              enter="ease-out duration-300"
              enterFrom="opacity-0 scale-95"
              enterTo="opacity-100 scale-100"
              leave="ease-in duration-200"
              leaveFrom="opacity-100 scale-100"
              leaveTo="opacity-0 scale-95"
            >
              <div className="inline-block mr-6 ml-6 mt-28 max-w-auto p-6 transition-all transform bg-gray-300 shadow-2xl rounded-3xl">
                <div className="flex m-w-fit max-auto">
                  <div className="py-2">
                    <div className="h-auto">
                      <Image
                        className=""
                        style={{ width: "100%", height: "auto" }}
                        src={image}
                        height={0}
                        width={0}
                        alt="ahojky"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </Transition.Child>
          </div>
        </Dialog>
      </Transition>
    </>
  );
};

export default WoodWorkBody;
