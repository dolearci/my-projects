import Head from "next/head";
import { React, useState } from "react";
import AddWoodWork from "../components/AddWoodWork";
import HeaderManagement from "../components/HeaderManagement";

const addwoodwork = () => {
  return (
    <div>
      <Head>
        <title>Stolárstvo Oli</title>
        <link rel="icon" href="/wood.ico" />
      </Head>
      <HeaderManagement></HeaderManagement>
      <AddWoodWork></AddWoodWork>
    </div>
  );
};

export default addwoodwork;
