import React from 'react';
import Image from 'next/image';
import Link from 'next/link';
import { getServerSession } from 'next-auth';

import { authOptions } from '@/server/auth';
import MovieSearch from '@/app/components/movie/movie-search';

import NavigationItem from './navigation-item';
import HamburgerMenu from './hamburger-menu';

type NavigationProps = {
	// TODO: navigation props
};

const Navigation: React.FC<NavigationProps> = async () => {
	const session = await getServerSession(authOptions);
	console.log('session:', session);

	return (
		<div className="flex w-full justify center ">
			<div className="flex w-full fixed top-0 p-2 justify-between items-center text-mh-theme bg-white font-headings z-50">
				{session?.user ? (
					<div className="lg:ml-40 md:ml-10 xl:flex hidden xl:w-1/3 justify-between space-x-4 text-lg">
						<NavigationItem href="/movie-picker">Movie Picker</NavigationItem>
						<NavigationItem href="/best-of">Best Of</NavigationItem>
					</div>
				) : (
					<div className="lg:ml-40 md:ml-10 md:flex hidden xl:w-1/3 justify-end space-x-4 text-lg">
						<NavigationItem href="/best-of">Best Of</NavigationItem>
					</div>
				)}

				<div className="flex w-full xl:w-1/3 justify-center space-x-6 md:space-x-4 items-center ">
					<NavigationItem href="/login">
						<Image
							src="/login.svg"
							className=""
							alt="Logo"
							width={40}
							height={52}
						/>
					</NavigationItem>
					<Link href="/">
						<div className="text-center">
							<h1 className="text-2xl  tracking-[9px] pl-[6px]">Movie</h1>
							<h1 className="text-3xl font-headings ">HUB</h1>
						</div>
					</Link>
					<div className="xl:block hidden">
						<NavigationItem>
							<MovieSearch />
						</NavigationItem>
					</div>
					<div className="block xl:hidden">
						<HamburgerMenu loggedIn={session?.user ? true : false} />
					</div>
				</div>
				{session?.user ? (
					<div className="lg:mr-40 md:mr-10 xl:flex hidden xl:w-1/3 justify-between space-x-6 text-lg">
						<NavigationItem href="/movie-lists">Movie Lists</NavigationItem>
						<NavigationItem href="/movie-lists/my-lists">
							My Movie Lists
						</NavigationItem>
					</div>
				) : (
					<div className="lg:mr-40 md:mr-10 xl:flex hidden xl:w-1/3 justify-start space-x-4 text-lg">
						<NavigationItem href="/movie-lists">Movie Lists</NavigationItem>
					</div>
				)}
			</div>
		</div>
	);
};

export default Navigation;
