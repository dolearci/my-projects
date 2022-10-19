import React from 'react';
import Link from 'next/link';

type NavigationItemProps = {
	href?: string;
	children: React.ReactNode;
};

const NavigationItem: React.FC<NavigationItemProps> = ({ href, children }) =>
	!href ? (
		<div className="py-3 px-4 hover:rounded-lg hover:bg-gray-100 transition-colors duration-300 ease-in-out">
			{children}
		</div>
	) : (
		<Link
			className="py-3 px-4 hover:rounded-lg hover:bg-gray-100 transition-colors duration-300 ease-in-out"
			href={href}
		>
			{children}
		</Link>
	);

export default NavigationItem;
