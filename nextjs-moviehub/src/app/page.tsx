'use client';
import 'react-toastify/dist/ReactToastify.css';
import { useRouter } from 'next/navigation';

const Home = () => {
	const router = useRouter();

	router.push('/best-of');
};

export default Home;
