import { useState } from "react";
import type { UrlDto } from "./model/UrlDto";

export default function App() {
  const [fullUrl, setFullUrl] = useState("");
  const [shortenAlias, setShortenAlias] = useState("");
  const [alias, setAlias] = useState("");
  const [shortUrl, setShortUrl] = useState("");
  const [urls, setUrls] = useState<UrlDto[]>([]);
  const [error, setError] = useState("");
  const [deletedMsg, setDeletedMsg] = useState("");

  const resetOnShortenForm = () => {
    setAlias("");
    setUrls([]);
    setError("");
  };

  const resetOnGetForm = () => {
    setShortenAlias("");
    setShortUrl("");
    setFullUrl("");
    setUrls([]);
    setError("");
  };

  const resetOnGetUrlsList = () => {
    setShortenAlias("");
    setShortUrl("");
    setFullUrl("");
    setAlias("");
    setError("");
  };

  const handleShorten = async () => {
    try {
      setError("");
      const res = await fetch("http://localhost:8080/shorten", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ fullUrl, customAlias: shortenAlias || undefined }),
      });
      if (!res.ok) throw new Error("Invalid input or alias already taken");
      const data = await res.json();
      setShortUrl(data.shortUrl);
    } catch (err: any) {
      setError(err.message);
    }
  };

  const handleGetUrl = async () => {
    try {
      setError("");
      window.location.href = `http://localhost:8080/${alias}`;
    } catch (err: any) {
      setError(err.message);
    }
  };

  const handleDeleteUrl = async () => {
    try {
      setError("");
      resetOnGetForm();
      const res = await fetch(`http://localhost:8080/${alias}`, { method: "DELETE" });
      if (!res.ok) throw new Error("Alias not found");
      setDeletedMsg(`Successfully deleted`);
    } catch (err: any) {
      setError(err.message);
    }
  };

  const handleGetAllUrl = async () => {
    try {
      setError("");
      resetOnGetUrlsList();
      const res = await fetch(`http://localhost:8080/urls`);
      const data = await res.json();
      setUrls(data);
    } catch (err: any) {
      setError(err.message);
    }
  };

  return (
    <div>
      <h2>Shorten URL</h2>
      <input placeholder="Full URL" value={fullUrl} onChange={e => {
        setFullUrl(e.target.value);
        setDeletedMsg("");
        resetOnShortenForm();
      }}/>
      <input placeholder="Custom alias (optional)" value={shortenAlias} onChange={e => {
        resetOnShortenForm();
        setDeletedMsg("");
        setShortenAlias(e.target.value);
      }}/>
      <button onClick={handleShorten}>Shorten</button>
      {shortUrl && <p>Shortened URL: {shortUrl}</p>}

      <h2>Get original URL</h2>
      <input placeholder="Alias" value={alias} onChange={e => { 
        setAlias(e.target.value);
        setDeletedMsg("");
        resetOnGetForm();
      }}/>
      <button onClick={handleGetUrl}>Get URL</button>
      <button onClick={handleDeleteUrl}>Delete URL</button>
      {deletedMsg && <p> {deletedMsg} </p>}

      <h2>List all URLs</h2>
      <button onClick={handleGetAllUrl}>Get URLs</button>
      {urls.length > 0 && (
        <ul>
          {urls.map((url) => (
            <li key={url.alias}>
              Alias: {url.alias} - Full Url: {url.fullUrl} - Shorten Url: {url.shortUrl}
            </li>
          ))}
        </ul>
      )}

      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}