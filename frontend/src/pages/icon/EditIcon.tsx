import React from "react";

function EditIcon() {
  return (
    <svg
      width="56"
      height="56"
      viewBox="0 0 56 56"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g filter="url(#filter0_ddd_353_1441)">
        <rect x="8" y="7" width="40" height="40" rx="8" fill="white" />
        <rect
          x="8.5"
          y="7.5"
          width="39"
          height="39"
          rx="7.5"
          stroke="#B1D1C3"
        />
      </g>
      <path
        d="M36.71 22.0425C37.1 21.6525 37.1 21.0025 36.71 20.6325L34.37 18.2925C34 17.9025 33.35 17.9025 32.96 18.2925L31.12 20.1225L34.87 23.8725L36.71 22.0425ZM19 32.2525V36.0025H22.75L33.81 24.9325L30.06 21.1825L19 32.2525Z"
        fill="#518A72"
      />
      <defs>
        <filter
          id="filter0_ddd_353_1441"
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
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.05 0"
          />
          <feBlend
            mode="normal"
            in2="BackgroundImageFix"
            result="effect1_dropShadow_353_1441"
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
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.05 0"
          />
          <feBlend
            mode="normal"
            in2="effect1_dropShadow_353_1441"
            result="effect2_dropShadow_353_1441"
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
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.1 0"
          />
          <feBlend
            mode="normal"
            in2="effect2_dropShadow_353_1441"
            result="effect3_dropShadow_353_1441"
          />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="effect3_dropShadow_353_1441"
            result="shape"
          />
        </filter>
      </defs>
    </svg>
  );
}

export default EditIcon;
