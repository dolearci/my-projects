/** @type {import('next').NextConfig} */
const nextConfig = { 
  reactStrictMode: true,
  swcMinify: true,
  images: {
    domains: [
      "upload.wikimedia.org",
      "avatars.dicebear.com",
      "images.pexels.com",
      "platform-lookaside.fbsbx.com",
      "static.xx.fbcdn.net",
      "scontent.xx.fbcdn.net",
      "scontent.fbts8-1.fna.fbcdn.net"
    ],
  },
}

module.exports = nextConfig
