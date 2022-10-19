'use-client';

import React from 'react';

type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement>;

const Button: React.FC<ButtonProps> = ({ children, className, ...props }) => (
	<button
		className={`text-lg font-weight-[600] text-mh-theme rounded border-[2px] border-mh-theme p-3 my-1 transition-colors duration-300 ease-in-out ${className}`}
		{...props}
	>
		{children}
	</button>
);

export default Button;
