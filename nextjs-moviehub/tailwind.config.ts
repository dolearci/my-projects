import type { Config } from 'tailwindcss';

const config: Config = {
	content: [
		'./src/pages/**/*.{js,ts,jsx,tsx,mdx}',
		'./src/components/**/*.{js,ts,jsx,tsx,mdx}',
		'./src/app/**/*.{js,ts,jsx,tsx,mdx}'
	],
	theme: {
		extend: {
			animation: {
				loading: 'loading 2s linear infinite',
				marquee: 'marquee 6s linear',
				fadeIn: 'fadeIn 1s ease-in-out',
				grow: 'spinnerGrow 1s ease-in-out',
				fadeInFast: 'fadeIn 0.3s ease-in-out'
			},
			keyframes: {
				loading: {
					'0%': { transform: 'translateX(-100%)' },
					'100%': { transform: 'translateX(100%)' }
				},
				marquee: {
					'0%': { transform: 'translateX(0)' },
					'100%': { transform: 'translateX(-100%)' }
				},
				fadeIn: {
					'0%': { opacity: '0' },
					'100%': { opacity: '1' }
				},
				fadeInFast: {
					'0%': { opacity: '0' },
					'100%': { opacity: '1' }
				},
				grow: {
					'0%': { opacity: '0' },
					'100%': { opacity: '1' }
				}
			},
			backgroundImage: {
				'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
				'gradient-conic':
					'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))'
			},
			fontFamily: {
				headings: ['Michroma', 'sans-serif'],
				content: ['Montserrat', 'sans-serif']
			},
			colors: {
				'headings': '#0C59F3',
				'mh-theme': 'rgb(31, 41, 55)'
			}
		}
	},
	plugins: [require('daisyui')],
	daisyui: {
		themes: ['light']
	}
};

export default config;
