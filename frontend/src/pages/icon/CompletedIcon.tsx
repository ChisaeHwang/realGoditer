import React from "react";

function CompletedIcon() {
  return (
    <svg
      width="56"
      height="56"
      viewBox="0 0 56 56"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g filter="url(#filter0_ddd_353_1431)">
        <rect
          x="48"
          y="7"
          width="40"
          height="40"
          rx="8"
          transform="rotate(90 48 7)"
          fill="white"
        />
        <path
          d="M22 24L28 30L34 24"
          stroke="#48A9EF"
          stroke-width="3"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <rect
          x="47.5"
          y="7.5"
          width="39"
          height="39"
          rx="7.5"
          transform="rotate(90 47.5 7.5)"
          stroke="#9CD5FF"
          stroke-linecap="round"
        />
      </g>
      <defs>
        <filter
          id="filter0_ddd_353_1431"
          x="0"
          y="0"
          width="56"
          height="56"
          filterUnits="userSpaceOnUse"
          color-interpolation-filters="sRGB"
        >
          <feFlood flood-opacity="0" result="BackgroundImageFix" />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset dy="1" />
          <feGaussianBlur stdDeviation="4" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0.639216 0 0 0 0 1 0 0 0 0.1 0"
          />
          <feBlend
            mode="normal"
            in2="BackgroundImageFix"
            result="effect1_dropShadow_353_1431"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset dy="2" />
          <feGaussianBlur stdDeviation="2" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0.639216 0 0 0 0 1 0 0 0 0.1 0"
          />
          <feBlend
            mode="normal"
            in2="effect1_dropShadow_353_1431"
            result="effect2_dropShadow_353_1431"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset dy="1" />
          <feGaussianBlur stdDeviation="1" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0.64 0 0 0 0 1 0 0 0 0.15 0"
          />
          <feBlend
            mode="normal"
            in2="effect2_dropShadow_353_1431"
            result="effect3_dropShadow_353_1431"
          />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="effect3_dropShadow_353_1431"
            result="shape"
          />
        </filter>
      </defs>
    </svg>
  );
}

export default CompletedIcon;
