/** @type {import('next').NextConfig} */
const nextConfig = {
	images: {
		remotePatterns: [
			{
				protocol: 'https',
				hostname: 'via.placeholder.com',
				port: '',
				pathname: '/**'
			},

			{
				protocol: 'https',
				hostname: 'image.tmdb.org',
				port: '',
				pathname: '/**'
			}
		]
	}
};

export default nextConfig;
