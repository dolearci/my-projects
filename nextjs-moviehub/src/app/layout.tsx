import React from 'react';
import type { Metadata, Viewport } from 'next';
import { Montserrat } from 'next/font/google';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import Navigation from './components/navigation/navigation';
import { Providers } from './providers';
import './globals.css';

const montserrat = Montserrat({ subsets: ['latin'] });

export const viewport: Viewport = {
	width: 'device-width',
	initialScale: 1,
	maximumScale: 1,
	userScalable: false
};

export const metadata: Metadata = {
	title: 'Movie Hub',
	description: 'Browse movies, get recommendations and share movie lists.',
	robots: {
		index: true,
		follow: true,
		googleBot: {
			index: true,
			follow: true
		}
	},
	keywords: ['Movie Hub', 'Movies', 'Movie lists']
};

const element = ({
	children
}: Readonly<{
	children: React.ReactNode;
}>) => (
	<html lang="en" className="bg-white">
		<body className={montserrat.className}>
			<header>
				<Navigation />
			</header>
			<ToastContainer position="bottom-right" />
			<div className="w-full h-full mt-4 flex justify-center bg-white">
				<div className="container pt-20 md:mx-20 md:pt-28 bg-white">
					<Providers>{children}</Providers>
				</div>
			</div>
		</body>
	</html>
);

export default element;
