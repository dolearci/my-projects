import Head from "next/head";
import Header from "../components/Header";
import WoodWorkList from "../components/WoodWorkList";
import { useState, useEffect } from "react";

export default function Home() {
  const WOODWORK_API = "http://localhost:8080/woodwork";
  let [loading, setLoading] = useState(true);
  let [woodworks, setWoodworks] = useState([]);
  let [query, setQuery] = useState("");
  let [type, setType] = useState("");

  const filterWoodWork = woodworks.filter((item) => {
    return (
      (item.type.toLowerCase().includes(query.toLowerCase()) ||
        item.description.toLowerCase().includes(query.toLowerCase()) ||
        item.color.toLowerCase().includes(query.toLowerCase()) ||
        item.id.toString().includes(query.toLowerCase())) &&
      item.type.toLowerCase().includes(type.toLowerCase())
    );
  });

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
        setWoodworks(woodworks);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, []);

  return (
    <div>
      <Head>
        <title>Stolárstvo Oli</title>
        <link rel="icon" href="/wood.ico" />
      </Head>
      <Header
        query={query}
        onQueryChange={(myQuery) => setQuery(myQuery)}
        onTypeChange={(myType) => setType(myType)}
      ></Header>
      <WoodWorkList loading={loading} woodworks={filterWoodWork}></WoodWorkList>
      <main></main>
    </div>
  );
}
